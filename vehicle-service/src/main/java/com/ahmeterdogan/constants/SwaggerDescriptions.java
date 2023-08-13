package com.ahmeterdogan.constants;

public interface SwaggerDescriptions {
    String DESCRIPTION1 = "Araç kayıt eden endpoint. Araçlar bir gruba ait olacak şekilde kayıt edilmeli. Sadece company" +
            " adminler araç kaydı yapabilir. Araç request'inde companyId verilmez. Aracı kayıt eden admin hangi company'e aitse" +
            " o company id otomatik olarak araca atanır";
    String DESCRIPTION2 = "Kullanıcıya araça yetkilendiren endpoint. Kullanıcı hali hazırda aracın bulunduğu grup üzerinde yetki" +
            "sahibi ise conflict döner.";
    String DESCRIPTION3 = "Kullanıcının gruplar üzerinden değil de doğrudan yetkili olduğu araçları liste yapısında dönen endpoint.";
    String DESCRIPTION4 = "userId ile verilen kullanıcının doğrudan yetkili olduğu araçları liste yapısında dönen endpoint." +
            " Sadece company adminleri kullanabilir.";
    String DESCRIPTION5 = "Company altındaki tüm araçları liste şeklinde dönen endpoint. Sadece company adminleri kullanabilir.";
}
