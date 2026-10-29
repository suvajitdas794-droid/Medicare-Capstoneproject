import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../core/auth.service';
@Component({standalone:true,imports:[FormsModule,RouterLink],template:`
<section class="auth-layout"><div class="auth-visual"><span class="eyebrow">A BETTER WAY TO SHOP</span><h1>Small steps,<br><em>more peace of mind.</em></h1><p>Create your account to save time at checkout and keep your order history close.</p><div class="auth-visual-mark">✚</div></div><form class="auth-card" (ngSubmit)="submit()"><span class="eyebrow">JOIN MEDICARE</span><h2>Create account</h2><p class="muted">It only takes a moment.</p>@if(error){<div class="alert">{{error}}</div>}<label>Full name<input name="name" [(ngModel)]="name" required maxlength="120" placeholder="Your name"></label><label>Email address<input name="email" type="email" [(ngModel)]="email" required placeholder="you@example.com"></label><label>Password<input name="password" type="password" [(ngModel)]="password" required minlength="8" placeholder="At least 8 characters"></label><button class="btn btn-primary full" [disabled]="busy">{{busy?'Creating account...':'Create account →'}}</button><p class="auth-switch">Already registered? <a routerLink="/login">Sign in</a></p></form></section>
`})
export class RegisterComponent {
 name='';email='';password='';error='';busy=false;
 constructor(private auth:AuthService,private router:Router){}
 submit(){this.busy=true;this.error='';this.auth.register(this.name,this.email,this.password).subscribe({next:()=>{this.busy=false;this.router.navigateByUrl('/');},error:e=>{this.busy=false;this.error=e.error?.message||'Could not create account.';}});}
}
