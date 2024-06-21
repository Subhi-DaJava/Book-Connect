/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { createFeedBack } from '../fn/feed-back/create-feed-back';
import { CreateFeedBack$Params } from '../fn/feed-back/create-feed-back';
import { getFeedbacksByBook } from '../fn/feed-back/get-feedbacks-by-book';
import { GetFeedbacksByBook$Params } from '../fn/feed-back/get-feedbacks-by-book';
import { PageResponseFeedbackResponse } from '../models/page-response-feedback-response';


/**
 * FeedBack API
 */
@Injectable({ providedIn: 'root' })
export class FeedBackService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `createFeedBack()` */
  static readonly CreateFeedBackPath = '/feedback';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `createFeedBack()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createFeedBack$Response(params: CreateFeedBack$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return createFeedBack(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `createFeedBack$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createFeedBack(params: CreateFeedBack$Params, context?: HttpContext): Observable<number> {
    return this.createFeedBack$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

  /** Path part for operation `getFeedbacksByBook()` */
  static readonly GetFeedbacksByBookPath = '/feedback/book/{bookId}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getFeedbacksByBook()` instead.
   *
   * This method doesn't expect any request body.
   */
  getFeedbacksByBook$Response(params: GetFeedbacksByBook$Params, context?: HttpContext): Observable<StrictHttpResponse<PageResponseFeedbackResponse>> {
    return getFeedbacksByBook(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getFeedbacksByBook$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getFeedbacksByBook(params: GetFeedbacksByBook$Params, context?: HttpContext): Observable<PageResponseFeedbackResponse> {
    return this.getFeedbacksByBook$Response(params, context).pipe(
      map((r: StrictHttpResponse<PageResponseFeedbackResponse>): PageResponseFeedbackResponse => r.body)
    );
  }

}
