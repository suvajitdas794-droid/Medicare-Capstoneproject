import { Component, OnInit } from '@angular/core';
import { DecimalPipe, DatePipe } from '@angular/common';
import { ApiService } from '../core/api.service';
import { CustomerOrder } from '../core/models';
@Component({standalone:true,imports:[DecimalPipe,DatePipe],template:`
<section class="page-wrap"><div class="page-title"><span class="eyebrow">YOUR MEDICARE ACCOUNT</span><h1>Order history</h1><p>See your orders and follow their latest status.</p></div>@if(error){<div class="alert">{{error}}</div>}<div class="orders-list">@for(o of orders;track o.id){<article class="order-card"><div class="order-head"><div><small>ORDER #{{o.id}}</small><h3>Placed {{o.createdAt | date:'mediumDate'}}</h3></div><span class="status-chip" [class]="statusClass(o.status)">{{o.status}}</span></div><div class="order-body"><div>@for(i of o.items;track i.id){<p>{{i.medicineName}} <span>× {{i.quantity}}</span></p>}</div><div class="order-total"><small>Order total</small><b>₹{{o.total | number:'1.2-2'}}</b></div></div><div class="order-foot"><span>⌖ {{o.shippingAddress}}</span><div class="status-track">@for(s of statuses;track s){<span [class.done]="statusIndex(o.status)>=statusIndex(s)">{{s}}</span>}</div></div></article>}@empty{<div class="empty-state"><span>◷</span><h2>No orders yet</h2><p>Your placed orders will appear here.</p><a class="btn btn-primary" href="/">Browse products →</a></div>}</div></section>
`})
export class OrdersComponent implements OnInit {
 orders:CustomerOrder[]=[];error='';statuses=['PLACED','CONFIRMED','PACKED','SHIPPED','DELIVERED'];
 constructor(private api:ApiService){}
 ngOnInit(){this.api.myOrders().subscribe({next:o=>this.orders=o,error:()=>this.error='Could not load your order history.'});}
 statusIndex(s:string){return this.statuses.indexOf(s);}
 statusClass(s:string){return 'status-'+s.toLowerCase();}
}
