import { Injectable } from '@angular/core';
import { CanActivate, Router, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { UserModel } from '../models/user.mode';
import { Observable, catchError, map, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard {

  constructor(
    private router: Router,
    private authService: AuthService
) {}

    canActivate(
        next: ActivatedRouteSnapshot,
        state: RouterStateSnapshot): Observable<boolean> {
        
        return this.authService.byId("6663bb5a20baf60a21b9945d").pipe(
            map((res: UserModel | undefined) => {
                if (res && res.role === "ADMIN") {
                return true;
                } else {
                this.router.navigate(['']);
                return false;
                }
            }),
            catchError((err) => {
                this.router.navigate(['']);
                return of(false);
            })
        );
    }
}
