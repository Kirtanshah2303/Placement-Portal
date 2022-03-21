import { Component } from 'react';
import Login from 'app/Demo/Login';
import React from 'react';

class DemoLogin extends Component {
  constructor(props) {
    super(props);
  }

  componentDidMount() {
    console.log('HAHAHAH welcome to demoLogin page');
    const cons = 'Kirtan';
    console.log(cons);
  }

  render() {
    return (
      <div>
        <h1>Hii</h1>
      </div>
    );
  }
}

export default DemoLogin;
