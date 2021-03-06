package com.clearbnb.repositories;

import com.clearbnb.entities.ResidenceInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResidenceInfoRepo extends CrudRepository<ResidenceInfo, Integer> {
    public ResidenceInfo findById(int id);

    public List<ResidenceInfo> findByCityContaining(String city);

    public List<ResidenceInfo> findByRegionContaining(String region);

    //public List<Residence> findByAddressId(int addressId);
    public static final String FIND_RESIDENCEBYADDRESSID = "SELECT r.id,\n" +
                "           r.price,\n" +
                "           r.rooms,\n" +
                "           r.max_guests,\n" +
                "           r.address_id,\n" +
                "           ci.country,\n" +
                "           ci.region,\n" +
                "           ci.city,\n" +
                "           a.city_id, \n" +
                "           a.zip_code,\n" +
                "           a.street_name,\n" +
                "           a.street_number,\n" +
                "           a.apartment_number,\n" +
                "           '' path \n" +
                "      FROM residences r,\n" +
                "           addresses a,\n" +
                "           cities ci\n" +
                "     WHERE r.address_id = a.id\n" +
                "       AND a.city_id = ci.id\n" +
                "      AND r.address_id = :address_id";
    @Query(value = FIND_RESIDENCEBYADDRESSID, nativeQuery = true)
    public List<ResidenceInfo> findByAddressId(int address_id);

    public static final String FIND_RESIDENCEBYCITYID = "SELECT r.id,\n" +
            "           r.price,\n" +
            "           r.rooms,\n" +
            "           r.max_guests,\n" +
            "           r.address_id,\n" +
            "           ci.country,\n" +
            "           ci.region,\n" +
            "           ci.city,\n" +
            "           a.city_id, \n" +
            "           a.zip_code,\n" +
            "           a.street_name,\n" +
            "           a.street_number,\n" +
            "           a.apartment_number,\n" +
            "           SUBSTR((SELECT p.path\n" +
            "               FROM photos p\n" +
            "               WHERE p.residence_id = re.id\n" +
            "               ORDER BY p.residence_id\n" +
            "               LIMIT 1),8) path\n" +
            "      FROM residences r,\n" +
            "           addresses a,\n" +
            "           cities ci\n" +
            "     WHERE r.address_id = a.id\n" +
            "       AND a.city_id = ci.id\n" +
            "       AND a.city_id = :city_id";
    @Query(value = FIND_RESIDENCEBYCITYID, nativeQuery = true)
    public List<ResidenceInfo> findByCityId(int city_id);

    public static final String FIND_CITIES = "SELECT distinct ci.id,ci.city " +
            "                                  FROM residences r, addresses a, cities ci " +
            "                                 WHERE r.address_id = a.id and a.city_id = ci.id" +
            "                                 order by ci.city";
    @Query(value = FIND_CITIES, nativeQuery = true)
    public List findAllCities();


    public static final String FIND_RESIDENCE_BY_SEARCH_PARAMETERS = "" +
            "select distinct re.id,\n" +
            "                re.rooms,\n" +
            "                re.price,\n" +
            "                re.max_guests,\n" +
            "                re.address_id,\n" +
            "                ci.country,\n" +
            "                ci.region,\n" +
            "                ci.city,\n" +
            "                ad.city_id,\n" +
            "                ad.zip_code,\n" +
            "                ad.street_name,\n" +
            "                ad.street_number,\n" +
            "                ad.apartment_number,\n" +
            "           SUBSTR((SELECT p.path\n" +
            "               FROM photos p\n" +
            "               WHERE p.residence_id = re.id\n" +
            "               ORDER BY p.residence_id\n" +
            "               LIMIT 1),8) path\n" +
            "           from residences re,\n" +
            "                addresses ad,\n" +
            "                cities ci\n" +
           /* "      LEFT JOIN bookings b ON re.id = b.residence_id\n" +*/
            "          where re.address_id = ad.id\n" +
            "            and ad.city_id = ci.id\n" +
            "            and ci.id = :city_id\n" +
            "            and re.max_guests >= :max_guest\n" +
            "            and not EXISTS(\n" +
            "                        select b.residence_id\n" +
            "                        from bookings b\n" +
            "                        where \n" +
            /*"                        ((:start_date < b.end_date and b.start_date < :start_date)\n" +
            "                or (:end_date < b.end_date and b.start_date < :end_date)\n" +
            "                or (b.start_date < :start_date and b.end_date > :start_date)\n" +
            "                   )\n" +*/
            "                   ((b.start_date <= :start_date and :start_date <= b.end_date) \n" +
            "                   or (:start_date <= b.start_date and b.start_date <= :end_date)\n" +
            "                   or (:start_date <= b.end_date and b.end_date <= :end_date ))\n" +
            "                and re.id = b.residence_id )\n" +
            "       order by re.id";
    //  "            and (((b.start_date > :end_date) AND (b.end_date < :start_date) or b.start_date is null) and :end_date > :start_date)\n" +
    @Query(value = FIND_RESIDENCE_BY_SEARCH_PARAMETERS, nativeQuery = true)
    public List<ResidenceInfo> findBySearchParameters(int city_id,
                                                      int start_date,
                                                      int end_date,
                                                      int max_guest);

    public static final String FIND_RESIDENCE_BY_OWNER_ID = "" +
            "        select distinct re.id,\n" +
            "                re.rooms,\n" +
            "                re.price,\n" +
            "                re.max_guests,\n" +
            "                re.address_id,\n" +
            "                ci.country,\n" +
            "                ci.region,\n" +
            "                ci.city,\n" +
            "                ad.city_id,\n" +
            "                ad.zip_code,\n" +
            "                ad.street_name,\n" +
            "                ad.street_number,\n" +
            "                ad.apartment_number,\n" +
            "           SUBSTR((SELECT p.path\n" +
            "               FROM photos p\n" +
            "               WHERE p.residence_id = re.id\n" +
            "               ORDER BY p.residence_id\n" +
            "               LIMIT 1),8) path\n" +
            "           from residences re,\n" +
            "                addresses ad,\n" +
            "                cities ci,\n" +
            "                owners_x_residences ow\n" +
            "          where re.address_id = ad.id\n" +
            "            and ad.city_id = ci.id\n" +
            "            and re.id = ow.residence_id\n" +
            "            and ow.owner_id = :owner_id";
    @Query(value = FIND_RESIDENCE_BY_OWNER_ID, nativeQuery = true)
    public List<ResidenceInfo> findByOwnerId(int owner_id);
}
