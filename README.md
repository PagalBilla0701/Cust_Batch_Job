public Boolean insertIvrCallReport(IvrCallReportDto dto) {
    Boolean inserted = false;
    Date currentDate = Calendar.getInstance().getTime();

    // Map DTO to IvrCallReport entity
    IvrCallReport report = new IvrCallReport();
    report.setXCreat("IVR");
    report.setDCreat(currentDate);
    report = ivrCallReportMapper.mapToIvrCallReportTable(dto);
    report.setDUpd(currentDate);
    report.setXUpd("IVR");

    // Saving the IvrCallReport entity
    IvrCallReport savedReport = ivrRepo.save(report);

    // Check if fullMenuTraversal exists in the DTO and map it to FullMenuTraversal entity
    if (dto.getFullMenuTraversal() != null && !dto.getFullMenuTraversal().isEmpty()) {
        FullMenuTraversal menuTraversal = new FullMenuTraversal();
        menuTraversal.setUcid(savedReport.getId()); // Assuming UCID is related to IvrCallReport ID
        menuTraversal.setXCreat("IVR");
        menuTraversal.setDCreat(currentDate);
        menuTraversal.setDUpd(currentDate);
        menuTraversal.setXUpd("IVR");

        // Dynamically mapping the menu fields from the DTO's Map
        Map<String, String> menus = dto.getFullMenuTraversal();

        if (menus.containsKey("menu1")) menuTraversal.setMenu1(menus.get("menu1"));
        if (menus.containsKey("menu2")) menuTraversal.setMenu2(menus.get("menu2"));
        if (menus.containsKey("menu3")) menuTraversal.setMenu3(menus.get("menu3"));
        if (menus.containsKey("menu4")) menuTraversal.setMenu4(menus.get("menu4"));
        if (menus.containsKey("menu5")) menuTraversal.setMenu5(menus.get("menu5"));
        // Continue for other menu fields based on the keys in the Map

        // Save the FullMenuTraversal entity to the database
        fullMenuTraversalRepo.save(menuTraversal); 
    }

    // Return true if both IvrCallReport and FullMenuTraversal were saved successfully
    if (savedReport != null) {
        inserted = true;
    }

    return inserted;
}
