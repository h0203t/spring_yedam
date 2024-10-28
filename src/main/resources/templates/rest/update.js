const employeeId = 100;

document.addEventListener("DOMContentLoaded", function(event){
  getEmpInfo(employeeId);

	document.getElementById('updateBtn')
					.addEventListener('click', updateEmpAJAX);

});
async function getEmpInfo()  {
  let emp = await fetch(`http://localhost:8099/yedam/emps/${employeeId}`)
                  .then(res => res.json())
                  .catch(err => console.log(err));

   updateForm.querySelectorAll('input')
             .forEach(tag =>{
                tag.value = tag.name == 'hireDate'? dateFormat(emp[tag.name]) : emp[tag.name];
             });               
}

function dateFormat(date){
  let hireDate = new Date(date);
  let year = hireDate.getFullYear();
  let month = ('0' + (hireDate.getMonth() + 1)).slice(-2);
  let day = ('0' + (hireDate.getDate())).slice(-2);
  return `${year}-${month}-${day}`;
}

function updateEmpAJAX(event){
  // 1) 보낼 데이터 결정
  let info = getFormData();

  // 2) AJAX
  fetch(`http://localhost:8099/yedam/emps/${employeeId}`,{
    method : 'put',
    //추가
    headers : {
      'Content-type' : 'application/json'
    },
    body : JSON.stringify(info)
  })
  .then(response => response.json() )
  .then(result =>{
    console.log(result);
  })
  .catch(err => console.log(err));

}

function getFormData(){
  let formData = new FormData(updateForm);
  
  let formObj = {};
  formData.forEach((value, name)=>{
    formObj[name] = value;
  });
  console.log(formObj);
  return formObj;
}