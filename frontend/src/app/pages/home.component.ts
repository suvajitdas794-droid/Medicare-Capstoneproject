import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { DecimalPipe } from '@angular/common';
import { ApiService } from '../core/api.service';
import { Medicine, CartItem } from '../core/models';
import { CartService } from '../core/cart.service';
@Component({standalone:true,imports:[FormsModule,DecimalPipe],template:`
<section class="hero"><div class="hero-copy"><span class="eyebrow">YOUR WELLNESS, OUR PRIORITY</span><h1>Care for every<br><em>day of your life.</em></h1><p>Explore healthcare essentials and everyday wellness products in one simple place.</p><div class="hero-actions"><a href="#catalog" class="btn btn-primary">Explore products <span>→</span></a><span class="trust">✓ Thoughtfully selected essentials</span></div></div><div class="hero-art"><div class="hero-circle"></div><div class="hero-cross">✚</div><div class="hero-pill pill-a"></div><div class="hero-pill pill-b"></div><div class="hero-note"><span>✦</span><div><b>Care made simple</b><small>Your essentials, all in one place</small></div></div></div></section>
<section class="benefits"><div><span>♡</span><b>Everyday essentials</b><small>Useful products for home care</small></div><div><span>⌁</span><b>Easy ordering</b><small>A simple, clear checkout flow</small></div><div><span>◷</span><b>Order updates</b><small>Track your order status</small></div></section>
<section id="catalog" class="catalog"><div class="section-heading"><div><span class="eyebrow">THE MEDICARE COLLECTION</span><h2>Shop healthcare essentials</h2><p>Search and filter the catalog to find what you need.</p></div><span class="result-count">{{medicines.length}} items</span></div>
<div class="filters"><div class="searchbox"><span>⌕</span><input [(ngModel)]="q" (input)="load()" placeholder="Search products or keywords..."></div><select [(ngModel)]="category" (change)="load()"><option value="">All categories</option>@for(c of categories;track c){<option [value]="c">{{c}}</option>}</select><select [(ngModel)]="brand" (change)="load()"><option value="">All brands</option>@for(b of brands;track b){<option [value]="b">{{b}}</option>}</select><input class="symptom" [(ngModel)]="symptom" (input)="load()" placeholder="Filter by symptom/tag"></div>
@if(error){<div class="alert">{{error}}</div>}
<div class="product-grid">@for(m of medicines;track m.id){<article class="product-card"><div class="product-image"><img [src]="m.imageUrl || fallback" [alt]="m.name" (error)="imageError($event)"><span class="product-category">{{m.category}}</span>@if(m.prescriptionRequired){<span class="rx-badge">Rx required</span>}</div><div class="product-info"><div class="product-brand">{{m.brand || 'Medicare Select'}}</div><h3>{{m.name}}</h3><p>{{m.description}}</p><div class="product-bottom"><div><strong>₹{{m.price | number:'1.2-2'}}</strong><small>{{m.stock>0?m.stock+' in stock':'Out of stock'}}</small></div><button class="add-btn" [disabled]="m.stock<1" (click)="add(m)">＋ Add</button></div></div></article>}@empty{<div class="empty-state"><span>⌕</span><h3>No matching products</h3><p>Try another search or clear your filters.</p><button class="btn btn-outline" (click)="clear()">Clear filters</button></div>}</div>
</section>
`})
export class HomeComponent implements OnInit {
 medicines:Medicine[]=[];q='';category='';brand='';symptom='';error='';fallback='https://images.unsplash.com/photo-1571781926291-c477ebfd024b?w=700';
 constructor(private api:ApiService,private cart:CartService){}
 ngOnInit(){this.load();}
 get categories(){return [...new Set(this.medicines.map(m=>m.category).filter(Boolean))].sort();}
 get brands(){return [...new Set(this.medicines.map(m=>m.brand).filter(Boolean))].sort();}
 load(){this.api.medicines({q:this.q,category:this.category,brand:this.brand,symptom:this.symptom}).subscribe({next:m=>{this.medicines=m;this.error='';},error:()=>this.error='Could not load products. Check that the backend and database are running.'});}
 add(m:Medicine){this.cart.add(m);this.error='Added to cart: '+m.name;}
 clear(){this.q='';this.category='';this.brand='';this.symptom='';this.load();}
 imageError(e:Event){(e.target as HTMLImageElement).src=this.fallback;}
}
