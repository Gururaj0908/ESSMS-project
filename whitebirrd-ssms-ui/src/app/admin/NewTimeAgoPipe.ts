import { TimeAgoPipe } from 'time-ago-pipe';
import { Pipe } from '@angular/core';

@Pipe({
    name: 'timeAgo',
    pure: false
})
export class NewTimeAgoPipe extends TimeAgoPipe {}