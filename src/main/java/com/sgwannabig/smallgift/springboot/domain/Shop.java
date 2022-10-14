package com.sgwannabig.smallgift.springboot.domain;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "shop")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Shop extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_Id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private Manager manager;

    //가게는 메뉴를 0 - 1 - many 가질 수 있다.
    @OneToMany(mappedBy = "shop")
    private List<Product> products = new ArrayList<Product>();


    //가게는 여러 리뷰를 가진다.
    @OneToMany(mappedBy = "shop")
    private List<Review> review = new ArrayList<Review>();


    //카테고리를 저장한다.
    @Column
    String category;

    //가게 이름을 저장한다
    @Column(name = "shop_name")
    String shopName;

    //가게 주소를 저장한다
    @Column(name = "shop_address")
    String shopAddress;

    //가게 전화번호를 저장한다.
    @Column(name = "shop_telephone")
    String shopTelephone;

    //가게의 승인 여부를 저장한다.
    @Enumerated(EnumType.STRING)
    ShopStatus status;


    //가게 대표메뉴 (한줄 미리보기 영역에 저장될 검색어)
    @Column(name ="main_menu")
    String mainMenu;

    //가게 영업시간 정보. 텍스트로 직접 설정하고 편집할 말을 적는다.
    @Column(name = "business_hours")
    String businessHours;

    //가게가 담은 총 좋아요 수
    @Column(name = "total_like")
    long totalLike;

    public void setManager(Manager manager) {
        if (this.manager != null) {
            this.manager.getShops().remove(this);
        }
        this.manager = manager;
        manager.getShops().add(this);
    }

    @Builder
    public Shop(long id, Manager manager, List<Product> products, List<Review> review,
        String category,
        String shopName, String shopAddress, String shopTelephone, ShopStatus status,
        String mainMenu,
        String businessHours, long totalLike) {
        this.id = id;
        this.manager = manager;
        this.products = products;
        this.review = review;
        this.category = category;
        this.shopName = shopName;
        this.shopAddress = shopAddress;
        this.shopTelephone = shopTelephone;
        this.status = status;
        this.mainMenu = mainMenu;
        this.businessHours = businessHours;
        this.totalLike = totalLike;
    }
}
