import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable, of} from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
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
   * Get all horses stored in the system
   *
   * @return observable list of found horses.
   */
  getAll(): Observable<Horse[]> {
    return this.http.get<Horse[]>(baseUri);
  }

  /** POST: add a new hero to the server */
  addHorse(horse: Horse): Observable<Horse> {
    return this.http.post<Horse>(baseUri, horse, this.httpOptions).pipe(
      catchError(this.handleError<Horse>('addHero'))
    );
  }

  updateHorses(horse: Horse): Observable<Horse> {
    return this.http.put<Horse>(baseUri, horse, this.httpOptions).pipe(
      catchError(this.handleError<Horse>('addHero'))
    );
  }

  getHorse(id: number): Observable<Horse> {
    return this.http.get<Horse>(baseUri + '/' + id);
  }

  deleteHorse(id: number): Observable<Horse>{
    return this.http.delete<Horse>(baseUri + '/' + id, this.httpOptions).pipe(
      catchError(this.handleError<Horse>('deleteHorse'))
    );
  }

  horseLookupMom(searchText: string): Observable<Horse[]>{
    return this.http.get<Horse[]>(baseUri + '/' + 'f' + '/' + searchText);
  }
  horseLookupDad(searchText: string): Observable<Horse[]>{
    return this.http.get<Horse[]>(baseUri + '/' + 'm' + '/' + searchText);
  }

  searchHorse(horse: Horse): Observable<Horse[]>{
    let uri = baseUri + '/search?';
    let addAnd = false;

    if(horse.name!==null){
      uri=uri+'name='+horse.name;
      addAnd=true;
    }
    if(horse.description!==null){
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
      uri=uri+'owner='+horse.owner;
    }

    return this.http.get<Horse[]>(uri);
  }

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   *
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      //this.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }
}
