import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../core/auth.service';
@Component({standalone:true,imports:[FormsModule,RouterLink],template:`
<section class="auth-layout"><div class="auth-visual"><span class="eyebrow">WELCOME BACK</span><h1>Your care,<br><em>all in one place.</em></h1><p>Sign in to place orders and follow your healthcare essentials from cart to delivery.</p><div class="auth-visual-mark">✚</div></div><form class="auth-card" (ngSubmit)="submit()"><span class="eyebrow">MEDICARE ACCOUNT</span><h2>Sign in</h2><p class="muted">Enter your details to continue.</p>@if(error){<div class="alert">{{error}}</div>}<label>Email address<input name="email" type="email" [(ngModel)]="email" required autocomplete="email" placeholder="you@example.com"></label><label>Password<input name="password" type="password" [(ngModel)]="password" required autocomplete="current-password" placeholder="Enter your password"></label><button class="btn btn-primary full" [disabled]="busy">{{busy?'Signing in...':'Sign in securely →'}}</button><p class="auth-switch">New to Medicare? <a routerLink="/register">Create an account</a></p><div class="demo-note"><b>Demo access</b><span>Admin: admin&#64;medicare.local / Admin&#64;12345</span><span>User: user&#64;medicare.local / User&#64;12345</span></div></form></section>
`})
export class LoginComponent {
 email='';password='';error='';busy=false;
 constructor(private auth:AuthService,private router:Router){}
 submit(){this.busy=true;this.error='';this.auth.login(this.email,this.password).subscribe({next:s=>{this.busy=false;this.router.navigateByUrl(s.role==='ADMIN'?'/admin':'/');},error:e=>{this.busy=false;this.error=e.error?.message||'Sign in failed. Please check your details.';}});}
}
