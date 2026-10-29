import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home.component';
import { LoginComponent } from './pages/login.component';
import { RegisterComponent } from './pages/register.component';
import { CartComponent } from './pages/cart.component';
import { OrdersComponent } from './pages/orders.component';
import { AdminComponent } from './pages/admin.component';
import { authGuard, adminGuard } from './core/guards';
export const routes:Routes=[
 {path:'',component:HomeComponent},
 {path:'login',component:LoginComponent},
 {path:'register',component:RegisterComponent},
 {path:'cart',component:CartComponent,canActivate:[authGuard]},
 {path:'orders',component:OrdersComponent,canActivate:[authGuard]},
 {path:'admin',component:AdminComponent,canActivate:[adminGuard]},
 {path:'**',redirectTo:''}
];
