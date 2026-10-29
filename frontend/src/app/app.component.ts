import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { AuthService } from './core/auth.service';
@Component({selector:'app-root',standalone:true,imports:[RouterLink,RouterLinkActive,RouterOutlet],template:`
<header class="topbar"><a class="brand" routerLink="/"><span class="brand-mark">✚</span><span>medicare<small>healthcare essentials</small></span></a>
<nav><a routerLink="/" routerLinkActive="active" [routerLinkActiveOptions]="{exact:true}">Shop</a>
@if(auth.isLoggedIn()){<a routerLink="/orders" routerLinkActive="active">My orders</a>}
@if(auth.isAdmin()){<a routerLink="/admin" routerLinkActive="active">Admin</a>}
@if(!auth.isLoggedIn()){<a routerLink="/login">Sign in</a><a class="nav-cta" routerLink="/register">Create account</a>}
@else {<span class="welcome">Hi, {{auth.session()?.name}}</span><button class="link-button" (click)="auth.logout()">Sign out</button>}
<a routerLink="/cart" class="cart-link">Cart <span>↗</span></a></nav></header>
<main><router-outlet/></main><footer><div><b>✚ medicare</b><span>Everyday healthcare essentials, delivered with care.</span></div><small>Demo capstone project · Not medical advice</small></footer>
`})
export class AppComponent {constructor(public auth:AuthService){}}
