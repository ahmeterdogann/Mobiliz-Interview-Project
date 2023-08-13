package com.ahmeterdogan.constants;

public interface SwaggerDescriptions {
    String DESCRIPTION1 = "Yeni bir grup kaydeden endpoint. root=true ise parenId'yi dikkate almaz. " +
            "root=false ise parentId'yi dikkate alır.";
    String DESCRIPTION2 = "Kullanıcıyı gruba yetkilendiren endpoint. Eğer kullanıcı yetkili olduğu bir gruba yetkilendirilmek " +
            "isteniyorsa conflict döner. Kullanıcının bir gruba yetkili olduğunda otomatik olarak o grubun tüm çocuklarına " +
            "yetkli olur. Yani her grup için ayrı ayrı kullanıcıya yönelik yetki kaydı tutulmaz. Grup ağacı oluşturulurken" +
            " kullanıcının yetkili olduğu gruplar ağaç yapısında tutulur.";
    String DESCRIPTION3 = "Kullanıcının yetkili olduğu grupları, gruptaki araçlar ile birlikte ağaç yapısında dönen endpoint" +
            " Kullanıcının doğrudan yetkili olduğu araçları içermez.";
    String DESCRIPTION4 = "Kullanıcının yetkili olduğu araçları liste yapısında dönen endpoint" +
            " Kullanıcının doğrudan yetkili olduğu araçları da içerir.";
    String DESCRIPTION5 = "userId ile verilen kullanıcının yetkili olduğu araçları liste yapısında dönen endpoint. Sadece company adminler kullanabilir";
    String DESCRIPTION6 = "Kullanıcının yetkili olduğu grupları liste yapısında dönen endpoint";
    String DESCRIPTION7 = "Grubu silen endpoint. Root grupların silinmesine izin verilmez. Bir grup silinmek istendiğinde" +
            " grupta bulunan araçlar silinen grubun parent'ının altına taşınır. Ayrıca silinen grubun bütün çocukları artık parent grubun çocuklarıdır" +
            " Sadece company adminler kullanabilir. Silinen grupla ilgili veritabanındaki tüm yetki kayıtları silinir.";
}
