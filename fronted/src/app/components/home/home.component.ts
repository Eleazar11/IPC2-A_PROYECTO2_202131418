import { Component } from '@angular/core';
import { Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { RegistroComponent } from "../registro/registro.component";

@Component({
  selector: 'app-home',
  standalone: true,
  //imports: [RouterLink, RouterLinkActive, RouterOutlet, RegistroComponent], unused import
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})

export class HomeComponent {

  constructor(private router: Router){

  }
navigateTo(arg0: string) {
  this.router.navigate([arg0]);
}
}
