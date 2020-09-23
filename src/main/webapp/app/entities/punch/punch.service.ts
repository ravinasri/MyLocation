import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPunch } from 'app/shared/model/punch.model';

type EntityResponseType = HttpResponse<IPunch>;
type EntityArrayResponseType = HttpResponse<IPunch[]>;

@Injectable({ providedIn: 'root' })
export class PunchService {
  public resourceUrl = SERVER_API_URL + 'api/punches';

  constructor(protected http: HttpClient) {}

  create(punch: IPunch): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(punch);
    return this.http
      .post<IPunch>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(punch: IPunch): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(punch);
    return this.http
      .put<IPunch>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPunch>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPunch[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(punch: IPunch): IPunch {
    const copy: IPunch = Object.assign({}, punch, {
      punchtime: punch.punchtime && punch.punchtime.isValid() ? punch.punchtime.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.punchtime = res.body.punchtime ? moment(res.body.punchtime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((punch: IPunch) => {
        punch.punchtime = punch.punchtime ? moment(punch.punchtime) : undefined;
      });
    }
    return res;
  }
}
