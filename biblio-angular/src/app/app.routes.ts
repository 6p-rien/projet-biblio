import { Routes } from '@angular/router';
import { CollecPage } from './page/collec-page/collec-page';
import { HomePage } from './page/home-page/home-page';

export const routes: Routes = [
    { path: 'home', component: HomePage },
    { path: 'collec', component: CollecPage },

    { path: '', redirectTo: 'home', pathMatch: 'full' }
];
