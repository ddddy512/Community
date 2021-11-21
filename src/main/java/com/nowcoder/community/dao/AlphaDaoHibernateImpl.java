package com.nowcoder.community.dao;

import org.springframework.stereotype.Repository;

@Repository("alphadaohibernate")
public class AlphaDaoHibernateImpl implements AlphaDao {
    public String select(){
        return "Hibernate";
    }
}
