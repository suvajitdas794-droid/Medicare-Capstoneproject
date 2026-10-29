import { Injectable, signal, computed } from '@angular/core';
import { Medicine, CartItem } from './models';
@Injectable({providedIn:'root'})
export class CartService {
 private itemsSignal=signal<CartItem[]>(this.read());
 items=computed(()=>this.itemsSignal());
 count=computed(()=>this.itemsSignal().reduce((s,i)=>s+i.quantity,0));
 total=computed(()=>this.itemsSignal().reduce((s,i)=>s+i.quantity*i.medicine.price,0));
 add(m:Medicine){this.itemsSignal.update(items=>{const found=items.find(i=>i.medicine.id===m.id);return found?items.map(i=>i.medicine.id===m.id?{...i,quantity:Math.min(i.quantity+1,m.stock)}:i):[...items,{medicine:m,quantity:1}];});this.persist();}
 change(id:number,qty:number){if(qty<=0){this.remove(id);return;}this.itemsSignal.update(items=>items.map(i=>i.medicine.id===id?{...i,quantity:Math.min(qty,i.medicine.stock)}:i));this.persist();}
 remove(id:number){this.itemsSignal.update(items=>items.filter(i=>i.medicine.id!==id));this.persist();}
 clear(){this.itemsSignal.set([]);this.persist();}
 private persist(){localStorage.setItem('medicare_cart',JSON.stringify(this.itemsSignal()));}
 private read():CartItem[]{try{return JSON.parse(localStorage.getItem('medicare_cart')||'[]') as CartItem[];}catch{return [];}}
}
