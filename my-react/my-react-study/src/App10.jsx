/*
{id:1, name:'小明', score:55},
{id:2, name:'小美', score:78},
{id:3, name:'小華', score:92},
{id:4, name:'阿強', score:40},

顯示 table 樣式
id | name | score | pass
 1   小明    55      X 
 2   小美    78      V 
 3   小華    92      V 
 4   阿強    40      X 
全班平均: XX

試著拆分父子組件
*/

function App() {
    const students = [
        {id:1, name:'小明', score:55},
        {id:2, name:'小美', score:78},
        {id:3, name:'小華', score:92},
        {id:4, name:'阿強', score:40},
    ];

    // 計算平均
    const avgScore = students.reduce((sum, student) => sum + student.score, 0) / students.length;

    return (
        <>
            <h1>學生成績表</h1>
            <table border="1">
                <thead>
                    <tr><th>ID</th><th>姓名</th><th>分數</th><th>及格</th></tr>
                </thead>
                <tbody>
                    {
                        students.map((student => {
                            const isPass = student.score >= 60;
                            return (
                                <tr key={student.id}>
                                    <td>{student.id}</td>
                                    <td>{student.name}</td>
                                    <td align="right">{student.score}</td>
                                    <td>{isPass ? "V" : "X"}</td>
                                </tr>
                            )
                        }))
                    }

                </tbody>
                <tfoot>
                    <tr>
                        <td colspan="2" align="right">平均</td>
                        <td align="right">{avgScore.toFixed(1)}</td>
                        <td></td>
                    </tr>
                </tfoot>
            </table>
        </>
    )
}

export default App;
