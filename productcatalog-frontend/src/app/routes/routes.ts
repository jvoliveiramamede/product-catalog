import { Routes } from "@angular/router";
import { LoginComponent } from "../login/login.component";
import { ProductListComponent } from "../product-list/product-list.component";
import { AuthGuard } from "../guard/auth.guard";
import { RegisterComponent } from "../register/register.component";

export const routes: Routes = [
    {
        path: '',
        component: LoginComponent
    },
    {
        path: 'products',
        component: ProductListComponent,
        canActivate: [AuthGuard]
    },
    {
        path: 'register',
        component: RegisterComponent
    }
]