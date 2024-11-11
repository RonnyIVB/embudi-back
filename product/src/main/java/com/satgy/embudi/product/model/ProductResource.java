package com.satgy.embudi.product.model;

import com.satgy.embudi.product.general.Str;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="productresource")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productresourceid")
    private Long productResourceId;

    @ManyToOne
    @JoinColumn(name = "productid", nullable = true, referencedColumnName = "productid", foreignKey=@ForeignKey(name = "FK_ProductResource_Product"))
    private Product product;

    @NotNull(message = "Por favor ingresa el orden del recurso")
    @Column(name = "\"order\"", nullable = false)
    private Short order;

    @NotNull(message = "Por favor ingresa el nombre del recurso")
    @Size(min = 3, max = 200, message = "El nombre debe tener de 3 a 200 caracteres")
    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @NotNull(message = "Especifica si es Principal")
    @Column(name = "main", nullable = false)
    private Boolean main;

    //@NotNull(message = "Por favor ingresa la descripción")
    @Size(max = 250, message = "La descripción puede tener hasta 250 caracteres")
    @Column(name = "descripcion", nullable = true, length = 250)
    private String description;

    @ManyToOne
    @JoinColumn(name = "fileid", nullable = true, referencedColumnName = "fileid", foreignKey=@ForeignKey(name = "FK_ProductResource_File"))
    private File file; // The resource could be a File or an URL

    @Size(max = 250, message = "La dirección web o URL puede tener hasta 250 caracteres")
    @Column(name = "url", nullable = true, length = 250)
    private String url;

    public String getEmbedUrl() {
        if (Str.esNulo(url)) return null;
        //https://www.youtube.com/watch?v=BXX8VfRcSQU
        //https://youtube.com/embed/BXX8VfRcSQU?autoplay=0
        //https://www.youtube.com/embed/lHVqoiG0CUU?si=nUDuDLJt6N0TRc8l
        //https://www.youtube.com/watch?v=L9WzNaMzfbE&list=PLProgi9ktvitYN3yGd1dxcdF3e9adz1AN
        if (url.contains("youtu") && url.contains("v=")) {
            int i1 = url.indexOf("v=")+2;
            int length = url.indexOf("&");
            if (length == -1) length = url.length();
            String code = url.substring(i1, length);
            return "https://youtube.com/embed/" + code + "?autoplay=0";
        } else if (url.contains("youtu") && url.contains("embed")) {
            int i1 = url.indexOf("embed")+6;
            int length = url.indexOf("?");
            if (length == -1) length = url.length();
            String code = url.substring(i1, length);
            return "https://youtube.com/embed/" + code + "?autoplay=0";
        }
        return url;
    }
}