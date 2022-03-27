import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {Owner} from '../dto/owner';
import {catchError} from 'rxjs/operators';


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
    return this.http.get<Owner[]>(baseUri).pipe(catchError((error: HttpErrorResponse) => {
      const errMsg = `Error Code: ${error.status}\nMessage: ${error.message}`;
      console.error(errMsg);
      alert(errMsg);
      return throwError(()=>error);
    }));
  }

  /**
   * @brief creates and inserts a new owner
   * @param owner that should be created
   * @return observable added owner.
   */
  addOwner(owner: Owner): Observable<Owner> {
    return this.http.post<Owner>(baseUri, owner, this.httpOptions).pipe(catchError((error: HttpErrorResponse) => {
      const errMsg = `Error Code: ${error.status}\nMessage: ${error.message}`;
      console.error(errMsg);
      alert(errMsg);
      return throwError(()=>error);
    }));
  }

  /**
   * @brief searches for owners matching the searchText
   * @param searchText part of an owner name
   * @return observable list of found owners.
   */
  ownerLookup(searchText: string): Observable<Owner[]>{
    if (searchText.length>0) {
      return this.http.get<Owner[]>(baseUri + '/' + 'lookup' + '/' + searchText).pipe(catchError((error: HttpErrorResponse) => {
        const errMsg = `Error Code: ${error.status}\nMessage: ${error.message}`;
        console.error(errMsg);
        return throwError(() => error);
      }));
    }
  }

  /**
   * @brief find a owner by its id
   * @param id of the specified owner
   * @return observable list with single owner.
   */
  getOwner(id: number): Observable<Owner>{
    return this.http.get<Owner>(baseUri + '/' + id).pipe(catchError((error: HttpErrorResponse) => {
      const errMsg = `Error Code: ${error.status}\nMessage: ${error.message}`;
      console.error(errMsg);
      alert(errMsg);
      return throwError(()=>error);
    }));
  }
}
