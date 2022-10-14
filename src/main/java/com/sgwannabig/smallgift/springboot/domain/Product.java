package com.sgwannabig.smallgift.springboot.domain;

import com.sun.istack.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product")
public class Product extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "product_id")
  private Long id;

  //가게는 많은 상품을 갖는다.
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "shop_id")
  private Shop shop;

  //상품은 여러 리뷰를 갖는다.
  @OneToMany(mappedBy = "product")
  private List<Review> review = new ArrayList<Review>();

  //상품은 여러개의 주문 내역을 가질 수 있다.
  @OneToMany(mappedBy = "product")
  private List<OrderDetails> orderDetails = new ArrayList<OrderDetails>();


  @Column(nullable = false)
  private String category;

  @Column(name = "product_name", nullable = false)
  private String productName;

  @Column(name = "product_content")
  private String productContent;

  @Column(name = "product_image")
  private String productImage;

  @Column(name = "product_price")
  private int productPrice;

  @Column(name = "discount_price")
  private int discountPrice;

  @Column(name = "product_stock", nullable = false)
  private long productStock;

  @Enumerated(value = EnumType.STRING)
  private ProductStatus status;

  @Column(name = "start_date", nullable = false)
  private LocalDate startDate;

  @Column(name = "end_date", nullable = false)
  private LocalDate endDate;

  @Builder
  public Product(Long id, Shop shop, List<Review> review, List<OrderDetails> orderDetails,
      String category, String productName, String productContent, String productImage,
      int productPrice, int discountPrice, long productStock, ProductStatus status,
      LocalDate startDate, LocalDate endDate) {
    this.id = id;
    this.shop = shop;
    this.review = review;
    this.orderDetails = orderDetails;
    this.category = category;
    this.productName = productName;
    this.productContent = productContent;
    this.productImage = productImage;
    this.productPrice = productPrice;
    this.discountPrice = discountPrice;
    this.productStock = productStock;
    this.status = status;
    this.startDate = startDate;
    this.endDate = endDate;
  }
}
