package ru.itmo.potatocoder228.weblab3;

import javax.annotation.ManagedBean;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Model;
import java.io.Serializable;

@ManagedBean
@ApplicationScoped
public class DataBean implements Serializable {
    public static final String persistenceUnit = "shots_db";



}
