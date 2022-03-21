import React from 'react';
import './demo2.scss';

class Demo2 extends React.Component {
  constructor(props) {
    super(props);
  }

  componentDidMount() {
    console.log('Hii');
  }

  render() {
    return <h1>Hiiiii</h1>;
  }
}
export default Demo2;
