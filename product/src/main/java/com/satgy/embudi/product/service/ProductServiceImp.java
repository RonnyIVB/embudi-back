package com.satgy.embudi.product.service;

import com.satgy.embudi.product.client.UserClientRest;
import com.satgy.embudi.product.dto.ProductAllData;
import com.satgy.embudi.product.dto.ProductGallery;
import com.satgy.embudi.product.dto.User;
import com.satgy.embudi.product.general.CustomResponse;
import com.satgy.embudi.product.general.CustomFilter;
import com.satgy.embudi.product.general.Str;
import com.satgy.embudi.product.model.Product;
import com.satgy.embudi.product.model.File;
import com.satgy.embudi.product.model.ProductResource;
import com.satgy.embudi.product.model.ProductStatus;
import com.satgy.embudi.product.repository.FileRepositoryI;
import com.satgy.embudi.product.repository.ProductRepositoryI;
import com.satgy.embudi.product.repository.ProductResourceRepositoryI;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImp implements ProductServiceI {

    private static final Logger log = LoggerFactory.getLogger(ProductServiceImp.class);

    @Autowired
    private ProductRepositoryI repo;

    @Autowired
    private FileRepositoryI repoFile;

    @Autowired
    private ProductResourceRepositoryI repoProductResource;

    @Autowired
    private UserClientRest userClientRest;

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Product> findById(Long id) {
        return repo.findById(id);
    }

    @Override
    public Optional<ProductAllData> getAllDataById(Long productId) {
        return repo.findById(productId).map(product -> {
            ProductAllData productAllData = new ProductAllData();
            productAllData.setProduct(product);

            List<ProductResource> prList = repoProductResource.findByProduct(productId);
            productAllData.setProductResourceList(prList);

            // I need the user data, by example the number phone for send message: 'I'm interested in this house'
            User user = userClientRest.findById(product.getUserId());
            product.setUser(user);

            return Optional.of(productAllData);
        }).orElseGet(Optional::empty);
    }

    @Override
    public CustomResponse customSearch(int pageNumber, int pageSize, String order, String uuid, List<CustomFilter> filters) {
        //String select = "Select p, pu from Producto p, ProductoUsuario pu ";
        //String where = " where p.idproducto = pu.producto.idproducto and pu.principal = true ";
        String select = "SELECT p, f " +
                "FROM Product p " +
                "LEFT JOIN ProductResource r ON r.product.productId = p.productId AND r.main = true " +
                "LEFT JOIN File f ON f.fileId = r.file.fileId ";
        String where = " WHERE p.productId >= 0 ";
        String whereUser = "";
        String whereStatus = "";
        String orderBy = " ORDER BY p.code, p.name asc ";

        if (!Str.esNulo(uuid)) {
            // for My Real Estates, it's neccesary that show all products regardless of the status
            User user = userClientRest.findByUuid(uuid);
            whereUser = " AND p.userId = " + user.getUserId() + " ";
        } else {
            // for the homepage, show only the available real estates
            whereStatus = " AND p.status = " + ProductStatus.Available.ordinal() + " "; // only search available real estates
        }

        String whereName = "", whereLandArea = "", whereLivableArea = "", whereBathrooms = "", whereBedrooms = "", whereParkingSpots = "";
        String wherePublicPrice = "", whereSector = "", whereOwnerName = "", whereType = "";

        for (CustomFilter f : filters) {
            if (f.getType().equals("name")) whereName = " AND (lower(p.name) like '%" + f.getLowerValue() + "%' OR lower(p.name) like '%" + f.getLowerValue() + "%' )";
            if (f.getType().equals("type")) whereType = " AND p.type.productTypeId = " + f.getValue() + " ";
            if (f.getType().equals("landArea")) {
                if (!Str.esNulo(f.getMin()) && !Str.esNulo(f.getMax())) whereLandArea = " AND p.landArea >= " + f.getMin() + " AND p.landArea <= " + f.getMax() + " ";
                else if (Str.esNulo(f.getMin()) && !Str.esNulo(f.getMax())) whereLandArea = " AND p.landArea <= " + f.getMax() + " ";
                else if (!Str.esNulo(f.getMin()) && Str.esNulo(f.getMax())) whereLandArea = " AND p.landArea >= " + f.getMin() + " ";
            }
            if (f.getType().equals("livableArea")) {
                if (!Str.esNulo(f.getMin()) && !Str.esNulo(f.getMax())) whereLivableArea = " AND p.livableArea >= " + f.getMin() + " AND p.livableArea <= " + f.getMax() + " ";
                else if (Str.esNulo(f.getMin()) && !Str.esNulo(f.getMax())) whereLivableArea = " AND p.livableArea <= " + f.getMax() + " ";
                else if (!Str.esNulo(f.getMin()) && Str.esNulo(f.getMax())) whereLivableArea = " AND p.livableArea >= " + f.getMin() + " ";
            }
            if (f.getType().equals("bathrooms")) {
                if (!Str.esNulo(f.getMin()) && !Str.esNulo(f.getMax())) whereBathrooms = " AND p.bathrooms >= " + f.getMin() + " AND p.bathrooms <= " + f.getMax() + " ";
                else if (Str.esNulo(f.getMin()) && !Str.esNulo(f.getMax())) whereBathrooms = " AND p.bathrooms <= " + f.getMax() + " ";
                else if (!Str.esNulo(f.getMin()) && Str.esNulo(f.getMax())) whereBathrooms = " AND p.bathrooms >= " + f.getMin() + " ";
            }
            if (f.getType().equals("bedrooms")) {
                if (!Str.esNulo(f.getMin()) && !Str.esNulo(f.getMax())) whereBedrooms = " AND p.bedrooms >= " + f.getMin() + " AND p.bedrooms <= " + f.getMax() + " ";
                else if (Str.esNulo(f.getMin()) && !Str.esNulo(f.getMax())) whereBedrooms = " AND p.bedrooms <= " + f.getMax() + " ";
                else if (!Str.esNulo(f.getMin()) && Str.esNulo(f.getMax())) whereBedrooms = " AND p.bedrooms >= " + f.getMin() + " ";
            }
            if (f.getType().equals("parkingSpots")) {
                if (!Str.esNulo(f.getMin()) && !Str.esNulo(f.getMax())) whereParkingSpots = " AND p.parkingSpots >= " + f.getMin() + " AND p.parkingSpots <= " + f.getMax() + " ";
                else if (Str.esNulo(f.getMin()) && !Str.esNulo(f.getMax())) whereParkingSpots = " AND p.parkingSpots <= " + f.getMax() + " ";
                else if (!Str.esNulo(f.getMin()) && Str.esNulo(f.getMax())) whereParkingSpots = " AND p.parkingSpots >= " + f.getMin() + " ";
            }
            if (f.getType().equals("publicPrice")) {
                if (!Str.esNulo(f.getMin()) && !Str.esNulo(f.getMax())) wherePublicPrice = " AND p.publicPrice >= " + f.getMin() + " AND p.publicPrice <= " + f.getMax() + " ";
                else if (Str.esNulo(f.getMin()) && !Str.esNulo(f.getMax())) wherePublicPrice = " AND p.publicPrice <= " + f.getMax() + " ";
                else if (!Str.esNulo(f.getMin()) && Str.esNulo(f.getMax())) wherePublicPrice = " AND p.publicPrice >= " + f.getMin() + " ";
            }
            if (f.getType().equals("ownerName")) whereOwnerName = " AND lower(p.ownerName) like '%" + f.getLowerValue() + "%' ";
            if (f.getType().equals("sector")) whereSector = " AND lower(p.sector) like '%" + f.getLowerValue() + "%' ";
        }

        String strQuery = select + where + whereStatus + whereUser + whereName + whereLandArea + whereLivableArea + whereBathrooms + whereBedrooms
                + whereParkingSpots + wherePublicPrice + whereSector + whereOwnerName + whereType + orderBy;
        log.info(" *********** QUERY: " + strQuery);
        Query q = em.createQuery(strQuery);
        List <Object[]>originalList = q.getResultList();
        List <ProductGallery> list = new ArrayList<>();
        for (Object [] vec : originalList) {
            ProductGallery e = new ProductGallery();
            e.setProduct((Product) vec[0]);
            if (vec[1] != null) e.setFile((File) vec[1]);
            list.add(e);
        }
        return CustomResponse.createFromQuery(pageSize, pageNumber, list);
    }

    public CustomResponse customSearchBack(int pageNumber, int pageSize, String order, String uuid, List<CustomFilter> filters) {
        //String select = "Select p, pu from Producto p, ProductoUsuario pu ";
        //String where = " where p.idproducto = pu.producto.idproducto and pu.principal = true ";
        String select = "SELECT p FROM Product p ";
        String where = " WHERE p.status = " + ProductStatus.Available.ordinal() + " "; // only search available real estates
        String userWhere = "";
        String orderBy = " ORDER BY p.code, p.name asc ";

        if (!Str.esNulo(uuid)) {
            User user = userClientRest.findByUuid(uuid);
            userWhere = " AND p.userId = " + user.getUserId() + " ";
        }

        String joinAval = "", whereAval = "", joinCategoria = "", whereCategoria = "";

        for (CustomFilter f : filters) {
            if (f.getType().equals("cat")) {
                if (whereCategoria.isEmpty()) whereCategoria = " pc.categoriaDetalle.idcategoriadetalle = " + f.getValue() + " ";
                else whereCategoria = whereCategoria + " OR pc.categoriaDetalle.idcategoriadetalle = " + f.getValue() + " ";
            }
            if (f.getType().equals("ava")) {
                if (whereAval.isEmpty()) whereAval = " pa.aval.idaval = " + f.getValue() + " ";
                else whereAval = whereAval + " OR pa.aval.idaval = " + f.getValue() + " ";
            }
        }
        if (!whereCategoria.isEmpty()) {
            joinCategoria = " JOIN ProductoCategoria pc ON pc.producto.idproducto = p.idproducto ";
            whereCategoria = " AND ( " + whereCategoria + " ) ";
            //joinCategoria = " JOIN ProductoCategoria pc ON pc.producto.idproducto = p.idproducto AND ( " + whereCategoria + " ) ";
            //whereCategoria = " ";
        }

        if (!whereAval.isEmpty()) {
            joinAval = " JOIN ProductoAval pa ON pa.producto.idproducto = p.idproducto ";
            whereAval = " AND ( " + whereAval + " ) ";
            //joinAval = " JOIN ProductoAval pa ON pa.producto.idproducto = p.idproducto  AND ( " + whereAval + " ) ";
            //whereAval = " ";
        }

        String strQuery = select + joinAval + joinCategoria + where + userWhere + whereAval + whereCategoria + orderBy;
        log.info(" *********** QUERY: " + strQuery);
        Query q = em.createQuery(strQuery);
        return CustomResponse.createFromQuery(pageSize, pageNumber, q.getResultList());

    }

    @Override
    @Transactional
    public Product create(Product product) {
        // The property userId isn't in the front. So it must be searched for.
        User user = userClientRest.findByUuid(product.getUser().getUuid());
        product.setUserId(user.getUserId());
        return repo.save(product);
    }

    @Override
    @Transactional
    public Product update(Product product) {
        return repo.save(product);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repoFile.deleteByProduct(id);
        repoProductResource.deleteByProduct(id);
        repo.deleteById(id);
    }
}
