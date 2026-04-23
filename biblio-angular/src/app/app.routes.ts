import { Routes } from '@angular/router';
import { CollecPage } from './page/collec-page/collec-page';
import { HomePage } from './page/home-page/home-page';

export const routes: Routes = [
    { path: 'home', component: HomePage, title: "Home Page" },
    { path: 'collec', component: CollecPage, title: "Liste des collections" },

    { path: '', redirectTo: 'home', pathMatch: 'full' }
];