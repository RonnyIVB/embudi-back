package com.satgy.embudi.product.service;

import com.satgy.embudi.product.client.UserClientRest;
import com.satgy.embudi.product.dto.User;
import com.satgy.embudi.product.general.CustomResponse;
import com.satgy.embudi.product.general.CustomFilter;
import com.satgy.embudi.product.general.Str;
import com.satgy.embudi.product.model.Product;
import com.satgy.embudi.product.model.ProductStatus;
import com.satgy.embudi.product.repository.ProductRepositoryI;
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
    private UserClientRest userClientRest;

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Product> findById(Long id) {
        return repo.findById(id);
    }

    @Override
    public CustomResponse customSearch(int pageNumber, int pageSize, String order, String uuid, List<CustomFilter> filters) {
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
                if (whereCategoria.isEmpty()) whereCategoria = " pc.categoriaDetalle.idcategoriadetalle = " + f.getId() + " ";
                else whereCategoria = whereCategoria + " OR pc.categoriaDetalle.idcategoriadetalle = " + f.getId() + " ";
            }
            if (f.getType().equals("ava")) {
                if (whereAval.isEmpty()) whereAval = " pa.aval.idaval = " + f.getId() + " ";
                else whereAval = whereAval + " OR pa.aval.idaval = " + f.getId() + " ";
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
        repo.deleteById(id);
    }
}
