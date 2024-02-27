import { Component } from '@angular/core';
import { RemoteService } from './services/remote.service';
import { Constants } from './constants';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'ssms-ui';
  theme = 'my-theme';
  constructor(private remote: RemoteService) {
    // remote.Relogin();
  }
}
