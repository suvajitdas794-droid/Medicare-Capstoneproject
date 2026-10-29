import { Injectable, signal, computed } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs';
import { UserSession } from './models';
@Injectable({providedIn:'root'})
export class AuthService {
 private readonly key='medicare_session';
 private sessionSignal=signal<UserSession|null>(this.read());
 session=computed(()=>this.sessionSignal());
 isLoggedIn=computed(()=>!!this.sessionSignal()?.token);
 isAdmin=computed(()=>this.sessionSignal()?.role==='ADMIN');
 constructor(private http:HttpClient){}
 login(email:string,password:string){return this.http.post<UserSession>('/api/auth/login',{email,password}).pipe(tap(s=>this.save(s)));}
 register(name:string,email:string,password:string){return this.http.post<UserSession>('/api/auth/register',{name,email,password}).pipe(tap(s=>this.save(s)));}
 logout(){localStorage.removeItem(this.key);this.sessionSignal.set(null);}
 private save(s:UserSession){localStorage.setItem(this.key,JSON.stringify(s));this.sessionSignal.set(s);}
 private read():UserSession|null{try{return JSON.parse(localStorage.getItem(this.key)||'null') as UserSession|null;}catch{return null;}}
}
