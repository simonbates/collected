# walk.py
#
# A Python program that walks through the MSAA hierarchy, printing
# information about each accessible object that it finds.
#
# Requirements: Python and comtypes
#
# (tested with Python 2.5 and comtypes 0.4.0 on Windows XP)
#
# comtypes
# http://sourceforge.net/projects/comtypes/
#
# Microsoft Active Accessibility
# http://msdn2.microsoft.com/en-us/library/ms697707.aspx
#
# Active Accessibility Client Interfaces and Functions
# http://msdn2.microsoft.com/en-us/library/ms696161.aspx
#
# IAccessible
# http://msdn2.microsoft.com/en-us/library/ms696165.aspx

from ctypes import windll, oledll, c_long, byref, POINTER, WINFUNCTYPE
from ctypes.wintypes import HWND, LPARAM
from comtypes.automation import VARIANT, VT_I4, VT_DISPATCH
from comtypes.client import GetModule
GetModule('oleacc.dll')
from comtypes.gen.Accessibility import IAccessible

# some MSAA constants
OBJID_WINDOW = 0
CHILDID_SELF = 0

roles = {
    0x1:  'title bar',
    0x2:  'menu bar',
    0x3:  'scroll bar',
    0x4:  'grip',
    0x5:  'sound',
    0x6:  'cursor',
    0x7:  'caret',
    0x8:  'alert',
    0x9:  'window',
    0xa:  'client',
    0xb:  'popup menu',
    0xc:  'menu item',
    0xd:  'tool tip',
    0xe:  'application',
    0xf:  'document',
    0x10: 'pane',
    0x11: 'chart',
    0x12: 'dialog',
    0x13: 'border',
    0x14: 'grouping',
    0x15: 'separator',
    0x16: 'tool bar',
    0x17: 'status bar',
    0x18: 'table',
    0x19: 'column header',
    0x1a: 'row header',
    0x1b: 'column',
    0x1c: 'row',
    0x1d: 'cell',
    0x1e: 'link',
    0x1f: 'help balloon',
    0x20: 'character',
    0x21: 'list',
    0x22: 'list item',
    0x23: 'outline',
    0x24: 'outline item',
    0x25: 'page tab',
    0x26: 'property page',
    0x27: 'indicator',
    0x28: 'graphic',
    0x29: 'static text',
    0x2a: 'text',
    0x2b: 'push button',
    0x2c: 'check button',
    0x2d: 'radio button',
    0x2e: 'combobox',
    0x2f: 'drop list',
    0x30: 'progress bar',
    0x31: 'dial',
    0x32: 'hotkey field',
    0x33: 'slider',
    0x34: 'spin button',
    0x35: 'diagram',
    0x36: 'animation',
    0x37: 'equation',
    0x38: 'button dropdown',
    0x39: 'button menu',
    0x3a: 'button dropdown grid',
    0x3b: 'whitepace',
    0x3c: 'page tab list',
    0x3d: 'clock',
    0x3e: 'split button',
    0x3f: 'ip address',
    0x40: 'outline button'
}

prototype = WINFUNCTYPE(None, HWND, LPARAM)

# passed to windll.user32.EnumWindows below to be called on each window
def callback(hwnd, lParam):
    """retrieve the IAccessible interface for the window hwnd and inspect it"""
    ppvObject = POINTER(IAccessible)()
    oledll.oleacc.AccessibleObjectFromWindow(hwnd, OBJID_WINDOW,
            byref(IAccessible._iid_), byref(ppvObject))
    inspect(ppvObject)

def inspect(ia, indent=0, indent_step=4):
    """print out the name, role, and number of children for the
    IAccessible ia and each of its children"""
    # retrieve the name, role, and number of children for ia
    selfID = VARIANT(CHILDID_SELF)
    accName = ia.accName[selfID]
    if accName == None or accName == '':
        accName = 'NAMELESS'
    else:
        accName = unicode(accName).encode('utf_8')
    accRole = ia.accRole[selfID]
    if accRole in roles:
        accRoleText = roles[accRole]
    else:
        accRoleText = str(accRole)
    accChildCount = ia.accChildCount
    print '%s%s (%s, %d children)' % (' ' * indent, accName, accRoleText, accChildCount)

    # recurse through the accessible children
    if accChildCount > 0:
        # allocate memory for information about the children
        # (VARIANT structures)
        VariantArrayType = VARIANT * accChildCount
        rgvarChildren = VariantArrayType()
        pcObtained = c_long()
        oledll.oleacc.AccessibleChildren(ia, 0, accChildCount, rgvarChildren,
                                         byref(pcObtained))
        # child.vt may be VT_I4 or VT_DISPATCH
        # see the MSDN documentation on the AccessibleChildren function
        # http://msdn2.microsoft.com/en-us/library/ms697243.aspx
        for child in rgvarChildren:
            if child.vt == VT_I4:
                try:
                    ppdispChild = ia.accChild(child)
                    if ppdispChild:
                        inspect(ppdispChild.QueryInterface(IAccessible),
                                indent + indent_step, indent_step)
                except:
                    pass
            elif child.vt == VT_DISPATCH:
                inspect(child.value.QueryInterface(IAccessible),
                        indent + indent_step, indent_step)

if __name__ == '__main__':
    # call our callback function on each window
    windll.user32.EnumWindows(prototype(callback), None)
