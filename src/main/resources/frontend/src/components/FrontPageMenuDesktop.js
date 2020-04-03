import React, { useState, useContext } from 'react';
import {Alert , Button, Form, Input, FormGroup } from 'reactstrap';
import Select from 'react-select';
import moment from 'moment'
import { ResidenceContext } from '../contexts/ResidenceContextProvider'
import { CityContext } from '../contexts/CityContextProvider'

import DatePicker from 'react-datepicker'
import 'react-datepicker/dist/react-datepicker.css'

const FrontPageMenuDesktop = () => {
  const today = new Date()
  const tomorrow = today.setDate(today.getDate()+1)

  const { setResidences } = useContext(ResidenceContext)

  //const [region, setRegion] = useState('')
  //const [city, setCity] = useState('')
  const [startDate, setStartDate] = useState(new Date())
  const [endDate, setEndDate] = useState(tomorrow)
  const [count_person, setCountPerson] = useState('')
  const [city_id, setCityId] = useState('');
  const [message, setMessage] = useState();

  const { cities } = useContext(CityContext)

  var someArrayOfStrings =[];
  someArrayOfStrings.map(opt => ({
      label: opt,
      value: opt
  }));
  const [visible, setVisible] = useState(false);

  const onDismiss = () => setVisible(false);

  const doSearch = async() => {
    const datas = {
                  //region,
                  //city,
                  city_id,
                  start_date: moment(startDate).format('X'),
                  end_date: moment(endDate).format('X'),
                  count_person
                  }
    console.log(datas.city_id+'-'+datas.start_date+'-'+datas.end_date+' person:'+datas.count_person);

    let res;
    if((!datas.city_id) || (!datas.start_date) || (!datas.end_date) || (!datas.count_person)) {
      res = await fetch('/api/clearbnb/residences')
      setMessage('Alla fält är obligatoriska!');
      setVisible(true);
    } else {
      res = await fetch('/api/clearbnb/residenceSearch/'+datas.city_id+'/'+datas.start_date+'/'+datas.end_date+'/'+datas.count_person+'')
    }
    res = await res.json()
    setResidences(res)
  }
  const CitiesSelect = () => {
    return (
      <div className="reactSelectDd">
        <Select options={cities} onChange = {opt => setCityId(opt.value)} placeholder="Vart vill du åka?" isSearchable required />
      </div>
      )
  }
 
  return (
    <div className="d-flex justify-content-center">
      <Form className="bg-secondary rounded p-1 form-inline" style={{marginTop: '5%'}}>
          <FormGroup>
            {CitiesSelect()}
          </FormGroup>
          <FormGroup className="mb-2 mr-sm-2 mb-sm-0">
            {/* <Input
              value={start_date} 
              onChange={e=>setStartDate(e.target.value)} 
              type="text" name="start_date" 
              id="start_date"
              placeholder="Startdatum"
              required /> */}
          <DatePicker
              className="p-2 rounded-lg"
                selected={startDate}
                onChange={date=>setStartDate(date)}
                dateFormat='MM/dd/yyyy'
                minDate={new Date()}
                isClearable
                showWeekNumbers
                showYearDropdown
                scrolllableMonthYearDropdown
                placeholderText=""
              />
          </FormGroup>
          <FormGroup className="mb-2 mr-sm-2 mb-sm-0">
          {/* <Input
              value={end_date} 
              onChange={e=>setEndDate(e.target.value)} 
              type="text" name="end_date" 
              id="end_date" 
              placeholder="Slutdatum"
              required /> */}
          <DatePicker
              className="p-2 rounded"
              style={{ padding:'2px !important'}}
                selected={endDate}
                onChange={date=>setEndDate(date)}
                dateFormat='MM/dd/yyyy'
                minDate={tomorrow}
                isClearable
                showWeekNumbers
                showYearDropdown
                scrolllableMonthYearDropdown
                placeholderText=""
              />
          </FormGroup>
          <FormGroup className="mb-2 mr-sm-2 mb-sm-0">
          <Input
              value={count_person}
              onChange={e=>setCountPerson(e.target.value)} 
              type="count_person" name="count_person" 
              id="count_person" 
              placeholder="Hur många gäster?"
              required  />
          </FormGroup>
          <Button onClick={doSearch} color="info" size="lg" >Sök</Button>          
          <Alert className="mb-1 ml-2 mr-sm-0 mb-sm-0" color="warning" isOpen={visible} toggle={onDismiss}>{message}</Alert>
      </Form>
    </div>
  )
}

export default FrontPageMenuDesktop