import {Component, Input} from '@angular/core';
import {NgForOf, NgIf} from "@angular/common";

@Component({
  selector: 'app-rating',
  standalone: true,
  imports: [
    NgForOf,
    NgIf
  ],
  templateUrl: './rating.component.html',
  styleUrl: './rating.component.scss'
})
export class RatingComponent {
  @Input() rating: number = 0;
  maxRating: number = 5;

  get fullStars(): number {
    return Math.floor(this.rating);
  }

  get hasHalfStars(): boolean {
    return this.rating % 1 !== 0;
  }

  // emptyStars is the difference between the maxRating and the rating, Math.ceil is used to round up the number;
  // for example, if the rating is 3.5, the emptyStars will be 1. 5-4 = 1; if the rating is 3.1, the emptyStars will be 2. 5-4 = 1;
  get emptyStars(): number {
    return this.maxRating - Math.ceil(this.rating);
  }
}
