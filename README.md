@Test
public void testSetMenusValue_PartialMenusPresent() {
    Map<String, String> menus = new HashMap<>();
    menus.put("Menu1", "Value1");
    menus.put("Menu2", "Value2");
    
    FullMenuTraversal menuTraversal = new FullMenuTraversal();

    setMenusValue(menuTraversal, menus);

    assertEquals("Value1", menuTraversal.getMenu1());
    assertEquals("Value2", menuTraversal.getMenu2());
    assertNull(menuTraversal.getMenu3());
    assertNull(menuTraversal.getMenu4());
}
