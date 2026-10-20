import { Routes } from '@angular/router';
import { Board } from './pages/board/board';
import { HomePage } from './pages/home-page/home-page';
import { SignIn } from './pages/sign-in/sign-in';

export const routes: Routes = [
    {path: 'board/:id', component: Board, pathMatch: 'full', title:'Board Page'},
    {path: '' , component: HomePage, pathMatch: 'full', title:'Home Page'},
    {path: 'sign-in', component: SignIn, pathMatch: 'full', title:'SignIn Page'}
];
