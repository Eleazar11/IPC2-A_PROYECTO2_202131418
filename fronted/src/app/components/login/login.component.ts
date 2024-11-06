import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-login',
  standalone: true,
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginData = {
    email: '',
    password: ''
  };
  constructor(private router: Router) {}

  // Método para navegar a la página de registro
  navigateTo(path: string) {
    this.router.navigate([path]);
  }
  
  
}

