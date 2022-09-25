import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { BookformComponent } from './bookform/bookform.component';
import { SavebookComponent } from './savebook/savebook.component';
import { RouterModule, Routes } from '@angular/router';
import { GetallauthorbooksComponent } from './getallauthorbooks/getallauthorbooks.component';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { authInterceptorProviders } from './_helpers/auth.interceptor';
import { ProfileComponent } from './profile/profile.component';

const routes:Routes = [
  {path: 'bookform', component: BookformComponent},
  {path: 'savebook', component: SavebookComponent},
  {path: 'getallauthorbooks', component: GetallauthorbooksComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'login', component: LoginComponent},
  {path: 'profile', component: ProfileComponent}
]
@NgModule({
  declarations: [
    AppComponent,
    BookformComponent,
    SavebookComponent,
    GetallauthorbooksComponent,
    RegisterComponent,
    LoginComponent,
    ProfileComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule, FormsModule, HttpClientModule, RouterModule.forRoot(routes)
  ],
  providers: [authInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }
