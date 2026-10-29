import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Medicine, CustomerOrder } from './models';
@Injectable({providedIn:'root'})
export class ApiService {
 constructor(private http:HttpClient){}
 medicines(filters:{q?:string;category?:string;brand?:string;symptom?:string}={}) {
  let params=new HttpParams(); Object.entries(filters).forEach(([k,v])=>{if(v)params=params.set(k,v);});
  return this.http.get<Medicine[]>('/api/medicines',{params});
 }
 saveMedicine(m:Partial<Medicine>){return m.id?this.http.put<Medicine>(`/api/medicines/${m.id}`,m):this.http.post<Medicine>('/api/medicines',m);}
 deleteMedicine(id:number){return this.http.delete<void>(`/api/medicines/${id}`);}
 placeOrder(shippingAddress:string,items:{medicineId:number;quantity:number}[]){return this.http.post<CustomerOrder>('/api/orders',{shippingAddress,items});}
 myOrders(){return this.http.get<CustomerOrder[]>('/api/orders/my');}
 allOrders(){return this.http.get<CustomerOrder[]>('/api/orders');}
 updateStatus(id:number,status:string){return this.http.patch<CustomerOrder>(`/api/orders/${id}/status`,{status});}
 dashboard(){return this.http.get<{users:number;medicines:number;orders:number}>('/api/admin/dashboard');}
 users(){return this.http.get<Array<{id:number;name:string;email:string;role:string;createdAt:string}>>('/api/admin/users');}
}
