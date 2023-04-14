package com.prueba_back.prueba_java.Repository;

import com.prueba_back.prueba_java.Entity.SystemKardex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KardexRepository extends JpaRepository<SystemKardex, Long> {

    @Query(value = """
    select  * from system_kardex sk
    where sk.quantity_tickets is not null
    and upper(sk.name) = upper(:name)
    order by sk.id_kardex ASC
    """,nativeQuery = true)
    List<SystemKardex> orderKardex(@Param("name")String name);

    @Query(value = "update system_kardex set quantity_tickets = :value where id_kardex = :id",nativeQuery = true)
    void updateSystemKardex(@Param("value")Long value, @Param("id")Long id);

    @Query(value = "select * from system_kardex sk where sk.id_kardex = :id",nativeQuery = true)
    SystemKardex findByIdSystem(@Param("id")Long id);
}
