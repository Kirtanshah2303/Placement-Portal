import React from 'react';
import { Component } from 'react';
import PrimarySearchAppBar from 'app/Components/Landing/navbar_landing';
import Landing from 'app/Components/Landing/landing_page';

class LandingPage extends Component {
  render() {
    return (
      <div>
        <PrimarySearchAppBar />
        <Landing />
      </div>
    );
  }
}

export default LandingPage;
