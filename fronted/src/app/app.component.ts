import { Component } from '@angular/core';
import { Router, RouterOutlet, RouterLink  } from '@angular/router';
import { HomeComponent } from "./components/home/home.component";
import { LoginComponent } from './components/login/login.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, LoginComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'fronted';
}
