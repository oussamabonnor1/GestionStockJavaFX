using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using StorageManagementAPI.Models;

namespace StorageManagementAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ArticlesController : ControllerBase
    {
        ArticleContext context;

        public ArticlesController(ArticleContext c )
        {
            this.context = c;
            if (context.articles.Count() == 0)
            {
                context.articles.Add(new Article{id = "1", label = "article 1", price = 120, minStock = 10});
                context.articles.Add(new Article{id = "2", label = "article 2", price = 120, minStock = 10});
                context.articles.Add(new Article{id = "3", label = "article 3", price = 120, minStock = 10});
            }
            context.SaveChanges();

        }

        // GET api/values
        [HttpGet]
        public async Task<ActionResult<IEnumerable<Article>>> Get()
        {
            return await context.articles.ToListAsync();
        }

        // GET api/values/5
        [HttpGet("{id}")]
        public async Task<ActionResult<Article>> Get(string id)
        {
            return await context.articles.FindAsync(id);
        }

        // POST api/values
        [HttpPost]
        public async Task<ActionResult<Article>> Post([FromBody] Article value)
        {
            context.articles.Add(value);
            await context.SaveChangesAsync();
            return CreatedAtAction("get", value);
        }

        // PUT api/values/5
        [HttpPut("{id}")]
        public async Task<ActionResult<Article>> Put(string id, [FromBody] Article value)
        {
            if (id != value.id)
            {
                return BadRequest("Ids dont match");
            }
            context.Entry(value).State = EntityState.Modified;
            await context.SaveChangesAsync();
            return Ok(value);
        }

        // DELETE api/values/5
        [HttpDelete("{id}")]
        public async Task<ActionResult<Article>> Delete(string id)
        {
            Article temp = await context.articles.FindAsync(id);
            if (temp == null)
            {
                return NotFound("Player not found");
            }
            context.articles.Remove(temp);
            await context.SaveChangesAsync();
            return Ok(temp);
        }
    }
}
