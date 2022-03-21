import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';
import PrivateRoute from 'app/shared/auth/private-route';
import { AUTHORITIES } from 'app/config/constants';
import Login from 'app/Demo/Login';
import Signin from 'app/Demo/Signin';
import DemoLogin from 'app/Demo/demoLogin';
import LandingPage from 'app/entities/Landing_Page/LandingPage';

import StudentProfile from './student-profile';
import CompanyProfile from './company-profile';
import StudentCompanyStatus from './student-company-status';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}student-profile`} component={StudentProfile} />
      <ErrorBoundaryRoute path={`${match.url}company-profile`} component={CompanyProfile} />
      <ErrorBoundaryRoute path={`${match.url}student-company-status`} component={StudentCompanyStatus} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      <ErrorBoundaryRoute path="/SignIn" component={Login} />
      <ErrorBoundaryRoute path="/SignUp" component={Signin} />
      <PrivateRoute path="/LandingPage" component={LandingPage} hasAnyAuthorities={[AUTHORITIES.USER]} />
    </Switch>
  </div>
);

export default Routes;
