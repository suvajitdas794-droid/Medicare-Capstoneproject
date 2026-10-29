import { HttpInterceptorFn } from '@angular/common/http';
export const authInterceptor:HttpInterceptorFn=(req,next)=>{
 const raw=localStorage.getItem('medicare_session');
 if(!raw)return next(req);
 try { const session=JSON.parse(raw); return next(req.clone({setHeaders:{Authorization:`Bearer ${session.token}`}})); }
 catch { return next(req); }
};
