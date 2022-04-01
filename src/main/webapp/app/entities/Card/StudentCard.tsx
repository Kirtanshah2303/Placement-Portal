import * as React from 'react';
import Box from '@mui/material/Box';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import { Component, useState } from 'react';
import { Row } from 'reactstrap';

const bull = (
  <Box component="span" sx={{ display: 'inline-block', mx: '2px', transform: 'scale(0.8)' }}>
    •
  </Box>
);

// export default function NewCard() {
//
//   const state = {
//     company : [],
//   }
//   let bearer = 'Bearer ';
//   let token = sessionStorage.getItem('jhi-authenticationToken');
//
//   token = token.slice(1, -1);
//
//   bearer = bearer + token;
//
//   fetch('/api/company-profiles', {
//     method: 'GET',
//     headers: {
//       accept: '*/*',
//       Authorization: bearer,
//     },
//   })
//     .then(response => response.json())
//     .then(result => {
//       // this.setState({ company: result });
//       this.setState({company : result})
//       console.log(this.state.company);
//     })
//     .catch(error => {
//       console.log(error);
//     });
//   return (
//     <Card sx={{ maxWidth: 200, border: 1, elevation:2, padding:'4px', paddingTop:'8px', }}>
//       <CardContent>
//         {/* <Typography sx={{ fontSize: 14 }} color="text.secondary" gutterBottom>
//           Word of the Day
//         </Typography> */}
//         <Typography variant="h5" component="div">
//           Crest Data Systems
//         </Typography>
//         <Typography sx={{ mb: 1.5 }} color="text.secondary">
//           No. of openings: 5
//         </Typography>
//         <Typography variant="body2">
//           Bond
//           <br />
//           {'Internship and jobs at ahmedabad'}
//         </Typography>
//       </CardContent>
//       <CardActions>
//         <Button size="small">Learn More</Button>
//       </CardActions>
//     </Card>
//   );
// }

class NewCard extends Component {
  state = {
    company: [],
  };
  constructor(props) {
    super(props);
  }

  componentDidMount() {
    let bearer = 'Bearer ';
    let token = sessionStorage.getItem('jhi-authenticationToken');

    token = token.slice(1, -1);

    bearer = bearer + token;

    fetch('/api/company-profiles', {
      method: 'GET',
      headers: {
        accept: '*/*',
        Authorization: bearer,
      },
    })
      .then(response => response.json())
      .then(result => {
        // this.setState({ company: result });
        this.setState({ company: result });
        console.log(this.state.company);
      })
      .catch(error => {
        console.log(error);
      });
  }

  render() {
    return (
      <Row>
        {this.state.company.map(company1 => (
          <Card key={company1.id} sx={{ maxWidth: 200, border: 1, elevation: 2, padding: '15px', paddingTop: '8px' }}>
            <CardContent>
              {/* <Typography sx={{ fontSize: 14 }} color="text.secondary" gutterBottom>
          Word of the Day
        </Typography> */}
              <Typography variant="h5" component="div">
                {company1.user.login}
              </Typography>
              <Typography sx={{ mb: 1.5 }} color="text.secondary">
                No. of openings: {company1.noOfOpenings}
              </Typography>
              <Typography variant="body2">
                Criteria :
                <br />
                {company1.minimumCriteria}
              </Typography>
            </CardContent>
            <CardActions>
              <Button size="small">Learn More</Button>
            </CardActions>
          </Card>
        ))}
      </Row>
    );
  }
}

export default NewCard;
