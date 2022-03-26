import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {environment} from 'src/environments/environment';
import {Horse} from '../dto/horse';

const baseUri = environment.backendUrl + '/horses';

@Injectable({
  providedIn: 'root'
})
export class HorseService {

  httpOptions = {
    headers: new HttpHeaders({ 'content-type': 'application/json' })
  };

  constructor(
    private http: HttpClient,
  ) { }

  /**
   * @brief Get all horses stored in the system
   * @return observable list of found horses.
   */
  getAll(): Observable<Horse[]> {
    return this.http.get<Horse[]>(baseUri).pipe(catchError((error: HttpErrorResponse) => {
      const errMsg = `Error Code: ${error.status}\nMessage: ${error.message}`;
      console.error(errMsg);
      alert(errMsg);
      return throwError(() => error);
    }));
  }

  /**
   * @brief creates and inserts a new horse
   * @param horse that should be created
   * @return observable added horse.
   */
  addHorse(horse: Horse): Observable<Horse> {
    return this.http.post<Horse>(baseUri, horse, this.httpOptions).pipe(catchError((error: HttpErrorResponse) => {
      const errMsg = `Error Code: ${error.status}\nMessage: ${error.message}`;
      console.error(errMsg);
      alert(errMsg);
      return throwError(()=>error);
    }));
  }

  /**
   * @brief changes information about an existing horse
   * @param horse with new information
   * @return observable updated horse.
   */
  updateHorses(horse: Horse): Observable<Horse> {
    return this.http.put<Horse>(baseUri, horse, this.httpOptions).pipe(catchError((error: HttpErrorResponse) => {
      const errMsg = `Error Code: ${error.status}\nMessage: ${error.message}`;
      console.error(errMsg);
      alert(errMsg);
      return throwError(()=>error);
    }));
  }

  /**
   * @brief find a horse by its id
   * @param id of the specified horse
   * @return observable list with single horse.
   */
  getHorse(id: number): Observable<Horse> {
    return this.http.get<Horse>(baseUri + '/' + id).pipe(catchError((error: HttpErrorResponse) => {
      const errMsg = `Error Code: ${error.status}\nMessage: ${error.message}`;
      console.error(errMsg);
      alert(errMsg);
      return throwError(()=>error);
    }));
  }

  /**
   * @brief deletes a horse from the system
   * @param id of the horse to delete
   * @return observable deleted horse.
   */
  deleteHorse(id: number): Observable<Horse>{
    return this.http.delete<Horse>(baseUri + '/' + id, this.httpOptions).pipe(catchError((error: HttpErrorResponse) => {
      const errMsg = `Error Code: ${error.status}\nMessage: ${error.message}`;
      console.error(errMsg);
      alert(errMsg);
      return throwError(()=>error);
    }));
  }

  /**
   * @brief searches for female horses matching the searchText
   * @param searchText part of a horse name
   * @return observable list of found horses.
   */
  horseLookupMom(searchText: string): Observable<Horse[]>{
    return this.http.get<Horse[]>(baseUri + '/' + 'f' + '/' + searchText).pipe(catchError((error: HttpErrorResponse) => {
      const errMsg = `Error Code: ${error.status}\nMessage: ${error.message}`;
      console.error(errMsg);
      alert(errMsg);
      return throwError(()=>error);
    }));
  }

  /**
   * @brief searches for male horses matching the searchText
   * @param searchText part of a horse name
   * @return observable list of found horses.
   */
  horseLookupDad(searchText: string): Observable<Horse[]>{
    return this.http.get<Horse[]>(baseUri + '/' + 'm' + '/' + searchText).pipe(catchError((error: HttpErrorResponse) => {
      const errMsg = `Error Code: ${error.status}\nMessage: ${error.message}`;
      console.error(errMsg);
      alert(errMsg);
      return throwError(()=>error);
    }));
  }

  /**
   * @brief searches for horses matching the parameters
   * @param horse parameters to match
   * @return observable list of found horses.
   */
  searchHorse(horse: Horse): Observable<Horse[]>{
    let uri = baseUri + '/search?';
    let addAnd = false;

    if(horse.name!==''){
      uri=uri+'name='+horse.name;
      addAnd=true;
    }
    if(horse.description!==''){
      if(addAnd) {uri=uri+'&';}
      uri=uri+'description='+horse.description;
      addAnd=true;
    }
    if(horse.birthdate!==null){
      if(addAnd) {uri=uri+'&';}
      const date = horse.birthdate.toISOString().substring(0,10);
      uri=uri+'birthdate='+date;
      addAnd=true;
    }
    if(horse.gender!==null){
      if(addAnd) {uri=uri+'&';}
      uri=uri+'gender='+horse.gender;
      addAnd=true;
    }
    if(horse.owner!==null){
      if(addAnd) {uri=uri+'&';}
      uri=uri+'owner='+horse.owner.toString();
    }

    return this.http.get<Horse[]>(uri).pipe(catchError((error: HttpErrorResponse) => {
      const errMsg = `Error Code: ${error.status}\nMessage: ${error.message}`;
      console.error(errMsg);
      alert(errMsg);
      return throwError(()=>error);
    }));
  }
}
