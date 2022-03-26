import { Injectable } from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Owner} from '../dto/owner';


const baseUri = environment.backendUrl + '/owners';

@Injectable({
  providedIn: 'root'
})
export class OwnerService {

  httpOptions = {
    headers: new HttpHeaders({ 'content-type': 'application/json' })
  };

  constructor(
    private http: HttpClient,
  ) { }

  /**
   * Get all horses stored in the system
   *
   * @return observable list of found horses.
   */
  getAll(): Observable<Owner[]> {
    return this.http.get<Owner[]>(baseUri);
  }

  /** POST: add a new hero to the server */
  addOwner(owner: Owner): Observable<Owner> {
    return this.http.post<Owner>(baseUri, owner, this.httpOptions);
  }

  ownerLookup(searchText: string): Observable<Owner[]>{
    return this.http.get<Owner[]>(baseUri + '/' + 'lookup' + '/' + searchText);
  }
}
