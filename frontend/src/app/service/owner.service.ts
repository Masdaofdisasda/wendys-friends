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
   * @brief Get all woners stored in the system
   * @return observable list of found owners.
   */
  getAll(): Observable<Owner[]> {
    return this.http.get<Owner[]>(baseUri);
  }

  /**
   * @brief creates and inserts a new owner
   * @param owner that should be created
   * @return observable added owner.
   */
  addOwner(owner: Owner): Observable<Owner> {
    return this.http.post<Owner>(baseUri, owner, this.httpOptions);
  }

  /**
   * @brief searches for owners matching the searchText
   * @param searchText part of an owner name
   * @return observable list of found owners.
   */
  ownerLookup(searchText: string): Observable<Owner[]>{
    return this.http.get<Owner[]>(baseUri + '/' + 'lookup' + '/' + searchText);
  }

  /**
   * @brief find a owner by its id
   * @param id of the specified owner
   * @return observable list with single owner.
   */
  getOwner(id: number): Observable<Owner>{
    return this.http.get<Owner>(baseUri + '/' + id);
  }
}
