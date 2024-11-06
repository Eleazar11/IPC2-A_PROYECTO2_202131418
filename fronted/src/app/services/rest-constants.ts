import { Injectable } from '@angular/core';
@Injectable({
  providedIn: 'root' // Esto hace que el servicio esté disponible en toda la aplicación
})
export class RestConstants {
  public readonly API_URL = 'http://localhost:8080/revistas-digitales-api/v1';

  public getApiURL(): string {
    return this.API_URL;
  }
}