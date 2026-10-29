export interface UserSession { token:string; name:string; email:string; role:'ADMIN'|'USER'; }
export interface Medicine {
 id:number; name:string; description:string; category:string; brand:string; symptoms:string;
 price:number; stock:number; prescriptionRequired:boolean; imageUrl:string; active:boolean;
}
export interface CartItem { medicine:Medicine; quantity:number; }
export interface OrderItem { id:number; medicineName:string; quantity:number; unitPrice:number; }
export interface CustomerOrder {
 id:number; status:string; total:number; shippingAddress:string; createdAt:string;
 user?:{id:number;name:string;email:string}; items:OrderItem[];
}
