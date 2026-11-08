import { Routes } from '@angular/router';
import { Board } from './pages/board/board';
import { HomePage } from './pages/home-page/home-page';
import { SignIn } from './pages/sign-in/sign-in';
import { SignUp } from './pages/sign-up/sign-up';
import { authGuardGuard } from './guards/auth-guard-guard';
import { Profile } from './pages/profile/profile';

export const routes: Routes = [
    {path: 'board/:id', component: Board, pathMatch: 'full', title:'Board Page', canActivate:[authGuardGuard]},
    {path: '' , component: HomePage, pathMatch: 'full', title:'Home Page', canActivate:[authGuardGuard]},
    {path: 'homepage', component: HomePage, canActivate: [authGuardGuard]},
    {path: 'profile', component: Profile, canActivate: [authGuardGuard]},
    {path: 'sign-in', component: SignIn, pathMatch: 'full', title:'Sign In Page'},
    {path: 'sign-up', component: SignUp,  title:'Sign Up Page'}
];
